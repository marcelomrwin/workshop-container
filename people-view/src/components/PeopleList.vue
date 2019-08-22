<template>
  <div class="col-12 col-md-10 col-lg-7">
    <div class="list-group list-group-flush">
      <div
        class="list-group-item d-flex align-items-start"
        v-for="item in people"
        v-bind:key="item.id"
      >
        <button
          class="mr-2 btn btn-sm btn-danger"
          title="Delete this people"
          @click="$emit('remove',item)"
        >
          <font-awesome-icon icon="trash" />
        </button>
        <div class="w-100">
          <div class="d-flex justify-content-between">
            <span class="h4 text-primary">{{item.id}}</span>
          </div>
          <div class="d-flex justify-content-between">
            <span class="font-weight-bold text-primary mr-1">Name:</span>
            <span
              contenteditable="contenteditable"
              @blur="$emit('edit',item.id, 'name', $event.target.innerText)"
            >{{item.name}}</span>
            <span class="font-weight-bold text-primary mr-1">Surname:</span>
            <span
              contenteditable="contenteditable"
              @blur="$emit('edit',item.id, 'surname', $event.target.innerText)"
            >{{item.surname}}</span>

            <span class="font-weight-bold text-primary mr-1">Age:</span>
            <span
              class="float-right"
              contenteditable="contenteditable"
              @blur="$emit('edit',item.id, 'age', $event.target.innerText)"
            >{{item.age}}</span>

            <span class="font-weight-bold text-primary mr-1">Email:</span>
            <span
              contenteditable="contenteditable"
              @blur="$emit('edit',item.id, 'email', $event.target.innerText)"
            >{{item.email}}</span>
          </div>
          <div class="d-flex justify-content-between">
            <span class="font-weight-bold text-primary mr-1">Inserted:</span>
            <span class="float-right">{{formattedDate(item.datein)}}</span>

            <span class="font-weight-bold text-primary mr-1">Updated:</span>
            <span class="float-right">{{formattedDate(item.updated)}}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

import moment from "moment";

export default {
  name: "PeopleList",
  props: ["people"],
  components: {
    FontAwesomeIcon
  },
  methods: {
    formattedDate: function(date) {
      return moment(new Date(date)).format("DD/MM/YYYY, h:mm a");
    }
  }
};
</script>