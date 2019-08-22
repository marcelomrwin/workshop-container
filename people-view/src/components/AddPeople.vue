<template>
  <div class="col-12">
    <div class="card textcenter mt-3">
      <div class="card-header bg-primary text-white" @click="hidepanel=!hidepanel">
        <font-awesome-icon icon="plus" class="mr-3" />Add People
      </div>

      <div class="card-body" :class="{'d-none': hidepanel}">
        <form id="aptForm" @submit.prevent="requestAdd">
          <div class="form-group form-row">
            <label class="col-md-2 col-form-label text-md-right" for="name">Name</label>
            <div class="col-md-10">
              <input
                type="text"
                class="form-control"
                name="name"
                id="name"
                placeholder="People Name"
                v-model="formData.name"
              />
            </div>
          </div>

          <div class="form-group form-row">
            <label class="col-md-2 col-form-label text-md-right" for="surname">Surname</label>
            <div class="col-md-10">
              <input
                type="text"
                class="form-control"
                id="surname"
                placeholder="Surname"
                v-model="formData.surname"
              />
            </div>
          </div>

          <div class="form-group form-row">
            <label class="col-md-2 col-form-label text-md-right" for="age">Age</label>
            <div class="col-md-4">
              <input type="number" class="form-control" id="age" v-model="formData.age" />
            </div>
          </div>

          <div class="form-group form-row">
            <label class="col-md-2 text-md-right" for="email">Email</label>
            <div class="col-md-10">
              <input
                type="text"
                class="form-control"
                name="email"
                id="email"
                placeholder="Email"
                v-model="formData.email"
              />
            </div>
          </div>

          <div class="form-group form-row mb-0">
            <div class="offset-md-2 col-md-10">
              <button type="submit" class="btn btn-primary d-block ml-auto">Add People</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

export default {
  name: "AddPeople",
  data() {
    return {
      formData: [],
      hidepanel: true
    };
  },
  components: {
    FontAwesomeIcon
  },
  methods: {
    requestAdd: function() {
      this.formData = {
        name: this.formData.name,
        surname: this.formData.surname,
        age: this.formData.age,
        email: this.formData.email
      };
      this.$emit("add", this.formData);
      this.formData = [];
      this.hidepanel = true;
    }
  }
};
</script>
<style>
.card-header {
  cursor: pointer;
}
</style>