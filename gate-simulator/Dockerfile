FROM node:8.16.2-alpine

WORKDIR /usr/src/app

COPY package*.json ./
RUN npm ci --production

COPY . .

EXPOSE 9999
CMD ["npm", "start"]