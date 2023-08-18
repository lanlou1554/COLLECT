import json

from flask import Flask
from flask_restful import Api, reqparse, request
from gevent import pywsgi
from docsimilarity.doc_similarity import get_doc_similarity_instance
import jieba
import jieba.analyse
sentencesfile = 'context.sentences'

doc_similarity = None



parser = reqparse.RequestParser()
parser.add_argument('strings', help='strings param required', required=True)

app = Flask(__name__)
api = Api(app)

def load_pc_from_sentencesfile(doc_sim):
    f = open(sentencesfile, 'r', encoding='utf-8')
    sentences = f.readlines()
    f.close()
    doc_sim.refresh_pc_with_sentences(sentences)


def save_sentences(sentences):
    f = open(sentencesfile, 'w', encoding='utf-8')
    sentences = list(map(lambda sentence: sentence.lstrip(), sentences))
    f.write('\n'.join(sentences))
    f.close()


@app.route('/docsim', methods=['POST', 'GET'])
def getSimilarities():
    if request.method == 'GET':
        return {"res": "open"}
    else:
        parser.parse_args()
        strings = request.json["strings"]
        sims = list(doc_similarity.cos_similarity_vector_with_exist_pc(strings, normalize=True))[1:]
        return {"res": sims}


@app.route('/refreshpc', methods=['POST', 'GET'])
def refreshPc():
    if request.method == 'GET':
        return {"res": "open"}
    else:
        parser.parse_args()
        strings = request.json["strings"]
        pc = (doc_similarity.refresh_pc_with_sentences(strings).tolist())[0]
        save_sentences(strings)
        return {"pc": pc}

@app.route('/tfidf',methods=['POST'])
def getTfidf():
    parser.parse_args()
    inputData = request.json["strings"]
    inputData = " ".join(inputData)
    jieba.analyse.set_stop_words(r'stopwords.txt')
    TFres = jieba.analyse.textrank(inputData, withWeight=True, topK=10)
    return {"tfidf":TFres}


if __name__ == "__main__":
    doc_similarity = get_doc_similarity_instance()
    server = pywsgi.WSGIServer(('0.0.0.0', 5000), app)
    server.serve_forever()