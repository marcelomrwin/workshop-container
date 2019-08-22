<template>
  <div id="main-app" class="container">
    <div class="row justify-content-center">
      <add-people @add="addPeople" />
      <people-list :people="filteredPeople" @remove="removePeople" @edit="editPeople" />
    </div>
  </div>
</template>

<script>
import PeopleList from "./components/PeopleList";
import AddPeople from "./components/AddPeople";
import _ from "lodash";
import axios from "axios";

const API_URL = process.env.VUE_APP_API_URL;

export default {
  name: "MainApp",
  data: function() {
    return {
      apiUrl: process.env.VUE_APP_API_URL,
      people: [],
      filterKey: "id",
      filterDir: "asc",
      searchTerms: ""
    };
  },
  components: {
    PeopleList,
    AddPeople
  },
  mounted() {
    axios.get(`${API_URL}`).then(response => (this.people = response.data));
  },
  computed: {
    searchedPeople: function() {
      return this.people.filter(item => {
        return (
          item.name.toLowerCase().match(this.searchTerms.toLowerCase()) ||
          item.surname.toLowerCase().match(this.searchTerms.toLowerCase()) ||
          item.email.toLowerCase().match(this.searchTerms.toLowerCase())
        );
      });
    },
    filteredPeople: function() {
      return _.orderBy(this.searchedPeople, item => {
        if (typeof item[this.filterKey].toLowerCase === "function") {
          return item[this.filterKey].toLowerCase();
        } else {
          return item[this.filterKey];
        }
      });
    }
  },
  methods: {
    addPeople: function(item) {
      axios
        .post(`${API_URL}`, item)
        .then(response => this.people.push(response.data));
    },
    editPeople: function(id, field, text) {
      const peopleIndex = _.findIndex(this.people, { id: id });
      this.people[peopleIndex][field] = text;
      var people = jsonCopy(this.people[peopleIndex]);
      people.id = null;
      axios
        .put(`${API_URL}/${id}`, people)
        .then(
          axios
            .get(`${API_URL}`)
            .then(response => (this.people = response.data))
        );
    },
    removePeople: function(item) {
      axios
        .delete(`${API_URL}/${item.id}`)
        .then((this.people = _.without(this.people, item)));
    },
    loadData: function() {
      axios.get(`${API_URL}`).then(response => (this.people = response.data));
    }
  }
};
function jsonCopy(obj) {
  return JSON.parse(JSON.stringify(obj));
}
</script>