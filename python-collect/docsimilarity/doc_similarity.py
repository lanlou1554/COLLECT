import jieba
import docsimilarity.sif.sifembedding as embedding
import docsimilarity.sif.util as sif_util
import numpy as np
import re
from docsimilarity.config import config_map


def get_cos_similar(v1: list, v2: list, normalize=False):
    num = np.dot(v1, v2)  # 向量点乘
    # print(num)
    denom = np.linalg.norm(v1) * np.linalg.norm(v2)  # 求模长的乘积
    # print(denom)
    cos_val = (num / denom) if denom != 0 else 0
    if (normalize):
        return (cos_val + 1) / 2
    else:
        return cos_val


def word_filter(word):
    return re.match('^[0-9\u4e00-\u9fa5]+$', word) is not None


class DocSimilarity:
    def __init__(self, wordfile, weightfile, spliter, weightpara=5e-3, rmpc=1, limit_words=None,
                 limit_weights=None) -> None:
        """
        :param wordfile: word vector file
        :param weightfile: each line is a word and its frequency
        :param weightpara: the parameter in the SIF weighting scheme, usually in the range [3e-5, 3e-3]
        :param rmpc: number of principal components to remove in SIF weighting scheme
        """
        # load word vectors
        (words, We) = sif_util.get_word_map(wordfile, limit=limit_words)

        # load word weights
        word2weight = sif_util.get_word_weights(weightfile, weightpara,
                                                limit=limit_weights)  # word2weight['str'] is the weight for the word 'str'
        weight4ind = sif_util.get_weight(words, word2weight)  # weight4ind[i] is the weight for the i-th word

        self.spliter = spliter
        self.words = words
        self.We = We
        self.word2weight = word2weight
        self.weight4ind = weight4ind
        self.pc = None
        self.rmpc = rmpc

    def refresh_pc_with_sentences(self, sentences):
        x, m = sif_util.sentences2idx(sentences, self.words,
                                      spliter=self.spliter,
                                      word_filter=word_filter)  # x is the array of word indices, m is the binary mask indicating whether there is a word in that location
        w = sif_util.seq2weight(x, m, self.weight4ind)  # get word weights
        self.pc = embedding.compute_sentences_pc(self.We, x, w, self.rmpc)
        return self.pc

    def get_embedding_with_exist_pc(self, sentences):
        if self.pc is None:
            return None
        x, m = sif_util.sentences2idx(sentences, self.words,
                                      spliter=self.spliter,
                                      word_filter=word_filter)  # x is the array of word indices, m is the binary mask indicating whether there is a word in that location
        w = sif_util.seq2weight(x, m, self.weight4ind)  # get word weights
        emb = embedding.SIF_embedding_with_exists_pc(self.We, x, w, self.pc, self.rmpc)
        return emb

    def cos_similarity_vector_with_exist_pc(self, sentences, normalize=False):
        """
        Args:
            sentences (list(str)): 一组句子
            normalize (boolean): 相似度是否归一化
        Return:
            返回第一个句子和句子数组中每一个句子的相似度，使用已有的pc
        """
        if self.pc is None:
            return None
        sentence_num = len(sentences)
        embedding = self.get_embedding_with_exist_pc(sentences)
        vector = np.zeros((sentence_num))
        for i in range(sentence_num):
            vector[i] = get_cos_similar(embedding[0], embedding[i], normalize=normalize)
        return vector


jieba_spliter = lambda sentence: list(jieba.cut(sentence))


def get_doc_similarity_instance(config='prod'):
    conf = config_map[config]
    doc_sim = DocSimilarity(conf['word_file'], conf['weight_file'], conf['split'], limit_words=conf['limit_words'])
    f = open(conf['sentences_file'], 'r', encoding='utf-8')
    sentences = f.readlines()
    f.close()
    doc_sim.refresh_pc_with_sentences(sentences)
    return doc_sim
