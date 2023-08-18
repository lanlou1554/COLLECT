import unittest
from docsimilarity.doc_similarity import get_doc_similarity_instance


class TestDocSimilarity(unittest.TestCase):
    def test_doc_similarity(self):
        doc_sim = get_doc_similarity_instance(config='dev')
        similarity_vector = doc_sim.cos_similarity_vector_with_exist_pc([
            '进入兴趣部落页面点击“周贡献榜” 出现“网页无法打开”。',
            '进入企鹅众测的兴趣部落,点击精华的标签，周贡献版等,显示无法打开页面 ',
            '进入发现的兴趣部落进入名人堂点击一个名人查看全部话题 显示必定是空白',
            '进入【发现】 点击【寻找小伙伴】 点击【兴趣部落】 点击“众测播报” 点击最下方的“去兴趣部落APP',
            '进入兴趣部落, 随便点开一个人发的帖子,拉到最下面点击去兴趣部落app,查看全部评论'

        ], normalize=True)
        print(similarity_vector)
        result = similarity_vector[0] > similarity_vector[1] > similarity_vector[2]
        self.assertTrue(result)


if __name__ == '__main__':
    unittest.main()
