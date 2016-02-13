'''main.py main method.'''
from parsers import Parser


if __name__ == '__main__':
  parser = Parser('fallbackdfa.in')
  parser.parse()
