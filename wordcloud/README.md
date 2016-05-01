This `wordcloud` distribution is a python program to create 3 wordclouds on text, metadata and languages found in Polar dataset respectively.

To run the entire setup on your local machine:

### Files present

1. wordcloud.py, setup.py
2. *.json: JSON data files

### Prerequisites:

1. Python 2.x or 3.x

### How to use:

1. `cd` to `wordcloud`
2. To install required python packages, run : `$python setup.py install`
3. To load data into Solr, run `$ python wordcloud.py -dir <data root directory>`
