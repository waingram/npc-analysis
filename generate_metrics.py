#!/usr/bin/env python3
"""
Evaluate citation extracting
"""
import numpy as np
from sklearn.metrics import f1_score, precision_score, recall_score

__author__ = "William A. Ingram"
__version__ = "0.1.0"
__license__ = "BSD3"


def main():
    """  """

    r_tags = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}
    map_dict = {'author': 0,
                'booktitle': 1,
                'date': 2,
                'editor': 3,
                'institution': 4,
                'journal': 5,
                'location': 6,
                'note': 7,
                'pages': 8,
                'publisher': 9,
                'title': 10,
                'volume': 11}

    npc_confusion = np.ndarray((len(r_tags), len(r_tags)), dtype=float)
    npc_confusion.fill(1)
    # print(npc_confusion)

    filename = '/Users/waingram/Projects/npc-analysis/results/npc_analysis.txt'
    f = open(filename, 'r')

    for lines in f:
        a = lines.strip().split()
        # print a
        if len(a) == 0:
            continue
        npc_confusion[map_dict[a[-2]]][map_dict[a[-1]]] = npc_confusion[map_dict[a[-2]]][map_dict[a[-1]]] + 1
        tp = np.zeros(len(set(r_tags)))
        fp = np.zeros(len(set(r_tags)))
        fn = np.zeros(len(set(r_tags)))
        npc_f1 = np.zeros(len(set(r_tags)))
        for j in range(len(set(r_tags))):
            tp[j] = npc_confusion[j][j]
            fp[j] = np.sum(npc_confusion, axis=1)[j] - tp[j]
            fn[j] = np.sum(npc_confusion, axis=0)[j] - tp[j]
            npc_f1[j] = float(2 * tp[j]) / float(2 * tp[j] + fn[j] + fp[j])

    print('True Distribution')
    print(np.sum(npc_confusion, axis=1))
    print('F1s')
    print(npc_f1)
    print('Print average')
    print(np.average(npc_f1))

    print("\ntype, true dist, fi")
    for i in range(0, 12):
        print(f"{[(k,v) for k,v in map_dict.items()][i][0]}, {(np.sum(npc_confusion, axis=1))[i]}, {npc_f1[i]}")

    print("\nAverage npc_f1")
    print(np.average(npc_f1))

    print(npc_confusion)

    npc_recall = np.diag(npc_confusion) / np.sum(npc_confusion, axis=1)
    npc_precision = np.diag(npc_confusion) / np.sum(npc_confusion, axis=0)

    print("Precision: %s" % npc_precision)
    print("Recall: %s" % npc_recall)


if __name__ == "__main__":
    """  """
    main()
