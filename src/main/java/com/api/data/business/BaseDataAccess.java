package com.api.data.business;

// #region Imports
import java.util.ArrayList;
import com.api.data.db.Connection;
import java.sql.SQLException;
import com.api.entities.business.BaseEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
// #endregion

public class BaseDataAccess {
    protected PreparedStatement preparedStatement;
    protected Statement statement = null;
    protected ResultSet resultSet = null;
    protected String query = null;

    protected <T extends BaseEntity> ArrayList<T> getManyWithoutStatement(DBToObject<ArrayList<T>> converter, String query, Object... parameters) throws SQLException {
        ArrayList<T> ResList = new ArrayList<>();
    	Statement st = null;
    	ResultSet rs = null;

        try {
        	// Dependiendo si tengo parámetros para la query abro un Statement o un PreparedStatement...
        	if (parameters == null) {
        		st = Connection.getInstancia().getConn().createStatement();

        		// ... y corre el query
                rs = st.executeQuery(query);
        	}
        	else {
        		st = Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        		int i = 0;

        		for (Object parameter : parameters) {
        			i++;

        			((PreparedStatement)st).setObject(i, parameter);
        		}

        		// ... y corre el query
                rs = ((PreparedStatement)st).executeQuery();
        	}

            ResList = converter.createFromDB(rs);
        }
        catch (SQLException ex) {
        	throw ex;
        }
		finally {
			Connection.getInstancia().closeConn();
		}

        return ResList;
    }

    protected <T extends BaseEntity> ArrayList<T> getMany(DBToObject<T> converter, String query, Object... parameters) throws SQLException {
        ArrayList<T> ResList = new ArrayList<>();
    	Statement st = null;
    	ResultSet rs = null;

        try {
        	// Dependiendo si tengo parámetros para la query abro un Statement o un PreparedStatement...
        	if (parameters == null) {
        		st = Connection.getInstancia().getConn().createStatement();

        		// ... y corre el query
                rs = st.executeQuery(query);
        	}
        	else {
        		st = Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        		int i = 0;

        		for (Object parameter : parameters) {
        			i++;

        			((PreparedStatement)st).setObject(i, parameter);
        		}

        		// ... y corre el query
                rs = ((PreparedStatement)st).executeQuery();
        	}

            while(rs.next())
                ResList.add((T)converter.createFromDB(rs));
        }
        catch (SQLException ex) {
        	throw ex;
        }
		finally {
			Connection.getInstancia().closeConn();
		}

        return ResList;
    }

    protected <T extends BaseEntity> T getOne(DBToObject<T> converter, String query, Object... parameters) throws SQLException {
    	T result = null;
    	Statement st = null;
    	ResultSet rs = null;

        try {
        	if (parameters == null) {
        		st = Connection.getInstancia().getConn().createStatement();

                rs = st.executeQuery(query);
        	}
        	else {
        		st = Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        		int i = 0;

        		for (Object parameter : parameters) {
        			i++;

        			((PreparedStatement)st).setObject(i, parameter);
        		}

                rs = ((PreparedStatement)st).executeQuery();
        	}

            if (rs.next())
                result = converter.createFromDB(rs);
        }
        catch (SQLException ex) {
        	throw ex;
        }
		finally {
			Connection.getInstancia().closeConn();
		}

        return result;
    }

    protected <T extends BaseEntity> T getOneWithoutStatement(DBToObject<T> converter, String query, Object... parameters) throws SQLException {
        T result = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            if (parameters == null) {
                st = Connection.getInstancia().getConn().createStatement();

                rs = st.executeQuery(query);
            }
            else {
                st = Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                int i = 0;

                for (Object parameter : parameters) {
                    i++;

                    ((PreparedStatement)st).setObject(i, parameter);
                }

                rs = ((PreparedStatement)st).executeQuery();
            }

            result = converter.createFromDB(rs);
        }
        catch (SQLException ex) {
            throw ex;
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return result;
    }

    protected <T extends BaseEntity> int update(String query, Object... parameters) throws SQLException {
    	int affectedRows = 0;
    	int result = 0;
    	PreparedStatement st = null;

    	try {
    		st = Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

    		int i = 0;

    		for (Object parameter : parameters) {
    			i++;

    			((PreparedStatement)st).setObject(i, parameter);
    		}

    		affectedRows = ((PreparedStatement)st).executeUpdate();

    		if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

    		ResultSet generatedKeys = st.getGeneratedKeys();

    		if (generatedKeys.next()) {
    			result = generatedKeys.getInt(1);
    		}
    	}
    	catch (SQLException ex) {
        	throw ex;
        }
    	finally {
			Connection.getInstancia().closeConn();
		}

    	return result;
    }

    protected int create(String query, Object... parameters) throws SQLException {
        int insertedId = 0;

        try {
            preparedStatement = Connection.getInstancia().getConn().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            int paramCount = 1;

            for (Object parameter : parameters) {
                preparedStatement.setObject(paramCount, parameter);
            }

            preparedStatement.executeUpdate();

            resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                insertedId = resultSet.getInt(1);
            }
        }
        catch(SQLException e) {
            throw e;
        }
        finally {
            Connection.getInstancia().closeConn();
        }

        return insertedId;
    }

    protected interface DBToObject<T> {
	       public T createFromDB(ResultSet rs) throws SQLException;
    }
}
