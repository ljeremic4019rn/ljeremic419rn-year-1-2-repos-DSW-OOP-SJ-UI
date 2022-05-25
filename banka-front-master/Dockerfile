FROM node:16.14

WORKDIR /usr/app

COPY package.json .
COPY package-lock.json .

RUN npm install

RUN mkdir node_modules/.cache && chmod -R 777 node_modules/.cache

COPY . .

USER node

CMD ["npm", "start"]

