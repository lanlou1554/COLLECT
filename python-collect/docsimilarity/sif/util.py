import numpy as np


def parse_word_vector_line(line):
    tokens = line.split()
    valid = True
    vector = []
    word = tokens[0]
    if len(tokens) == 301:
        try:
            j = 1
            while j < 301:
                vector.append(float(tokens[j]))
                j += 1
        except:
            valid = False
    else:
        valid = False
    return valid, word, vector


def get_word_map(world_vector_file, limit=None):
    words = {}
    we = []
    with open(world_vector_file, 'r', encoding='utf-8') as f:
        idx = 0
        for n, line in enumerate(f):
            (valid, word, word_vector) = parse_word_vector_line(line)
            if valid:
                words[word] = idx
                we.append(word_vector)
                idx += 1
            else:
                print(f'invalid line {n}, skipped, start with {word}')
            if n % 50000 == 0:
                print(f"loading word vector ({n}/{limit if limit is not None else '???'})")
            if limit is not None and n >= limit:
                break
    # words['UUUNKKK'] = idx
    # we.append(list(np.zeros((300,))))
    return words, np.array(we)


def get_word_weights(weightfile, a=1e-3, limit=None):
    if a <= 0:  # when the parameter makes no sense, use unweighted
        a = 1.0
    word2weight = {}
    with open(weightfile, encoding='utf-8') as f:
        N = 0
        for lineNo, line in enumerate(f):
            line = line.strip()
            if len(line) > 0:
                tokens = line.split()
                if len(tokens) == 2:
                    word2weight[tokens[0]] = float(tokens[1])
                    N += float(tokens[1])
                else:
                    print(f'invalid word weight line: {line}')
            if lineNo % 500000 == 0:
                print(f"loading word weights ({lineNo}/{limit if limit is not None else '???'})")
            if limit is not None and lineNo > limit:
                break
        for key, value in word2weight.items():
            word2weight[key] = a / (a + value / N)
    return word2weight


def get_weight(words, word2weight):
    weight4ind = {}
    for word, ind in words.items():
        if word in word2weight:
            weight4ind[ind] = word2weight[word]
        # elif word == 'UUUNKKK':
        #     weight4ind[ind] = 0
        else:
            weight4ind[ind] = 1
    return weight4ind


def prepare_data(list_of_seqs):
    lengths = [len(s) for s in list_of_seqs]
    n_samples = len(list_of_seqs)
    maxlen = np.max(lengths)
    x = np.zeros((n_samples, maxlen)).astype('int32')
    x_mask = np.zeros((n_samples, maxlen)).astype('float32')
    for idx, s in enumerate(list_of_seqs):
        x[idx, :lengths[idx]] = s
        x_mask[idx, :lengths[idx]] = 1.
    x_mask = np.asarray(x_mask, dtype='float32')
    return x, x_mask


def lookupIDX(words, w):
    w = w.lower()
    if len(w) > 1 and w[0] == '#':
        w = w.replace("#", "")
    if w in words:
        return words[w]
    print(f'word \'{w}\' not found!')
    if 'UUUNKKK' in words:
        return words['UUUNKKK']
    else:
        return len(words) - 1


def default_word_filter(word):
    return word != ' '


def getSeq(p1, words, spliter, word_filter):
    if spliter is None:
        p1 = p1.split()
    else:
        p1 = spliter(p1)
    p1 = list(filter(word_filter, p1))
    X1 = []
    for i in p1:
        X1.append(lookupIDX(words, i))
    return X1


def sentences2idx(sentences, words, spliter=None, word_filter=default_word_filter):
    """
    Given a list of sentences, output array of word indices that can be fed into the algorithms.
    :param sentences: a list of sentences
    :param words: a dictionary, words['str'] is the indices of the word 'str'
    :param spliter: 分割器，默认以空格分割，可以使用分词工具
    :param word_filter: 筛除停止词
    :return: x1, m1. x1[i, :] is the word indices in sentence i, m1[i,:] is the mask for sentence i (0 means no word at the location)
    """
    seq1 = []
    for i in sentences:
        seq1.append(getSeq(i, words, spliter, word_filter))
    x1, m1 = prepare_data(seq1)
    return x1, m1


def seq2weight(seq, mask, weight4ind):
    weight = np.zeros(seq.shape).astype('float32')
    for i in range(seq.shape[0]):
        for j in range(seq.shape[1]):
            if mask[i, j] > 0 and seq[i, j] >= 0:
                weight[i, j] = weight4ind[seq[i, j]]
    weight = np.asarray(weight, dtype='float32')
    return weight
