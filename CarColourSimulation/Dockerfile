
FROM python:3.10.12


WORKDIR /code


COPY ./code/requirements.txt /code/requirements.txt


RUN pip install --no-cache-dir --upgrade -r /code/requirements.txt


COPY ./code /code/app


CMD ["fastapi", "run", "app/main.py", "--port", "80"]