FROM node:24-alpine as build
WORKDIR /app
COPY package.json .
RUN npm install
COPY . .
RUN npm run build


FROM nginx:stable-alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
ENV VITE_BACKEND_URL=http://localhost:8080
CMD ["nginx", "-g", "daemon off;"]