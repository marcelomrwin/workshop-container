#!/bin/bash
# You must certify that the commands below is executed in your environment
# npm install -g npm
# npm install -g @vue/cli

export PROJECT=$1

if [ -z "$PROJECT" ]
then
      printf "Variable PROJECT is empty.\nUse create_project.sh <project-name>"
      exit 1
fi

echo "creating project $PROJECT"

vue create $PROJECT-view
cd $PROJECT-view
npm i --save bootstrap jquery popper.js
npm i --save lodash moment axios
npm i --save @vue/cli-service
npm i --save vuejs-dialog
npm i --save dotenv-webpack
npm i --save @fortawesome/fontawesome-free @fortawesome/fontawesome-svg-core @fortawesome/free-solid-svg-icons @fortawesome/vue-fontawesome
npm i --save portfinder@1.0.21
