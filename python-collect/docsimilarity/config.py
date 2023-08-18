import os
import jieba
sentences_file = 'context.sentences'
word_file_name = 'sgns.merge.word'
weight_file_name = 'sgns.merge.word.freq'
prod_data_dir = '/pythonProject'
dev_data_dir = 'D:/Documents/se_learning/Project_pratice/SECIII2022/algrithm/data'
jieba_spliter = lambda sentence: list(jieba.cut(sentence))

prod_config = {
    'word_file': os.path.join(prod_data_dir, word_file_name),
    'weight_file': os.path.join(prod_data_dir, weight_file_name),
    'sentences_file': sentences_file,
    'split': jieba_spliter,
    'limit_words': 100000,

}

dev_config = {
    'word_file': os.path.join(dev_data_dir, word_file_name),
    'weight_file': os.path.join(dev_data_dir, weight_file_name),
    'sentences_file': os.path.join(dev_data_dir, sentences_file),
    'split': jieba_spliter,
    'limit_words': 100000
}

config_map = {
    'prod': prod_config,
    'dev': dev_config
}