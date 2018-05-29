<template>
    <form @submit.prevent="save()" class="box">
        <b-field label="Name">
            <b-input
            placeholder="Type a name"
            v-model="name"/>
        </b-field>
        <b-field grouped position="is-right">
            <p class="control">
                <button class="button" type="button" @click="reset()" v-if="editing">
                    Reset
                </button>
                <button class="button is-primary">
                    {{ editing ? "Edit" : "Create" }} new brand
                </button>
            </p>
        </b-field>
    </form>
</template>

<script>
import API from "@/helpers/api.js";

export default {
    data: () => {
        return {
            name: null,
            id: null,
            editing: false
        }
    },
    methods: {
        getBrand(id) {
            return new API().get("/brands/" + id, null);
        },
        save() {
            let data = {
                id: this.id,
                name: this.name
            };

            new API().post("/brands", data).then(() => {
                this.editing = false;
                this.$router.push("/brands");
            })
            .catch(error => {
                console.log(error);
            });
        },
        reset() {
            this.name = null;
            this.id = null;
            this.editing = false;
            this.$router.push("/brands");
        }
    },
    mounted() {
        let id = this.$route.params.id;

        if (!id || Number.isNaN(Number(id)))
            return;

        this.editing = true;

        this.getBrand(id).then(response => {
            let brand = response.data;

            this.id = brand.id;
            this.name = brand.name;
        })
        .catch(error => {
            console.log(error);
        });
    }
}
</script>
