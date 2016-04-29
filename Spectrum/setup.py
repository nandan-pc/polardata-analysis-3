import os
from setuptools import setup

def read(fname):
    return open(os.path.join(os.path.dirname(__file__), fname)).read()

setup(
    name = "Spectrum",
    version = "0.1",
    author = "Team 9, CSCI 599",
    description = ("A package to find measurement tags across files and visualize their range"),
    keywords = "ner,measurements,spectrum,range",
    use_2to3=True,
    long_description=read('README.md'),
    install_requires=['nltk','tika'],
)
