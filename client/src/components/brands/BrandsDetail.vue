<template>
    <section class="section">
        <div class="container">
            <form @submit.prevent="save()">
                <b-field grouped>
                    <b-field label="ID" expanded v-if="editing">
                        <b-input readonly
                        placeholder="Type an id"
                        v-model="id"/>
                    </b-field>
                    <b-field label="Name" expanded>
                        <b-input
                        placeholder="Type a name"
                        v-model="name"/>
                    </b-field>
                </b-field>
                <b-field grouped position="is-right">
                    <p class="control">
                        <a class="button is-danger is-outlined" @click="remove" :disabled="!editing">
                            <span class="icon">
                                <i class="fas fa-trash"></i>
                            </span>
                        </a>
                        <button class="button is-outlined is-dark" type="button" @click="goBack()">
                            Cancel
                        </button>
                        <button class="button is-success is-outlined">
                            Save
                        </button>
                    </p>
                </b-field>
            </form>
        </div>
    </section>
</template>

<script>
import API from "@/helpers/api.js";
import * as Notifier from "@/helpers/notifier.js";

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
                this.goBack();
            })
            .catch(error => {
                console.log(error);
            });
        },
        goBack() {
            this.$router.push("/brands");
        },
        remove() {
            this.$dialog.confirm({
                title: 'Deleting brand',
                message: 'Are you sure you want to <b>delete</b> this brand?',
                confirmText: 'Delete Brand',
                type: 'is-danger',
                hasIcon: true,
                onConfirm: () => {
                    this.loading = true;

                    new API().delete("/brands/" + this.id).then(() => {
                        Notifier.success("Brand was deleted.");
                        this.errors = [];
                        this.loading = false;
                        this.goBack();
                    })
                    .catch(error => {
                        this.loading = false;
                        this.errors = error.response.data;
                    });
                }
            });
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
        });
    }
}
</script>
