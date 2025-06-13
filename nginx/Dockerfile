# Dockerfile for latest Nginx
FROM nginx:latest

# Copy custom nginx configuration if you have one
COPY nginx.conf /etc/nginx/nginx.conf

# Copy static files if needed
# COPY ./html /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
