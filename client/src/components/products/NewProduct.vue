<template>
    <section class="section">
      <div class="container">
        <div class="columns">
          <div class="column is-three-quarters">
            <b-field horizontal label="Name" message="Please provide a clear and descriptive name">
                <b-input name="name" expanded></b-input>
            </b-field>
          </div>
        </div>
        <div class="columns">
          <div class="column is-half">
            <b-field horizontal label="Brand">
                <ws-option-filter filter="brands"></ws-option-filter>
            </b-field>
          </div>
          <div class="column is-half">
            <b-field horizontal label="Category">
                <ws-option-filter filter="categories"></ws-option-filter>
            </b-field>
          </div>
        </div>
        <div class="columns">
          <div class="column is-10">
            <b-field horizontal label="Description">
                <b-input type="textarea"></b-input>
            </b-field>

            <b-field horizontal><!-- Label left empty for spacing -->
                <p class="control">
                    <button class="button is-primary">
                      Save
                    </button>
                </p>
            </b-field>
          </div>
        </div>
      </div>
    </section>
</template>

<script>
import WsOptionFilter from "@/components/ws-framework/WsOptionFilter.vue";

export default {
  name: 'new-product',
  components: {
    WsOptionFilter
  }
}
</script>
