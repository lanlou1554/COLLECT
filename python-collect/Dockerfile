FROM frolvlad/alpine-python-machinelearning
RUN pip install -i https://pypi.tuna.tsinghua.edu.cn/simple --upgrade pip
WORKDIR /app
COPY . /app
RUN pip install -i https://pypi.tuna.tsinghua.edu.cn/simple -r requirements.txt
EXPOSE 5000
ENTRYPOINT ["python"]
CMD ["main.py"]