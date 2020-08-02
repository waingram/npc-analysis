#!/usr/bin/env python3
"""
Evaluate citation extracting
"""
from sklearn.metrics import confusion_matrix, classification_report

__author__ = "William A. Ingram"
__version__ = "0.1.0"
__license__ = "BSD3"


def main():
    """  """

    filename = '../results/npc_analysis.txt'
    f = open(filename, 'r')

    labels = ['author',
              'booktitle',
              'date',
              'editor',
              'institution',
              'journal',
              'location',
              'note',
              'pages',
              'publisher',
              'title',
              'volume']

    y_true = []
    y_pred = []

    for lines in f:
        a = lines.strip().split()
        if len(a) == 0:
            continue
        y_pred.append(a[-2])
        y_true.append(a[-1])


    print('Confusion Matrix')
    print(confusion_matrix(y_true, y_pred, labels=labels))

    print('\nClassification Report')
    print(classification_report(y_true, y_pred, labels=labels, ))


if __name__ == "__main__":
    """  """
    main()
