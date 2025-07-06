FROM python:3.9-slim
WORKDIR /app
COPY reg-list.py .
RUN pip install flask docker
CMD ["python", "reg-list.py"]
