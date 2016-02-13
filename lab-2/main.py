'''main.py main method.'''
import sys
from parsers import Parser

if __name__ == '__main__':

  parser = Parser('fallbackdfa.in', 'Lab2.in')
  dfa = parser.parse_dfa()
  inputs = parser.parse_test_inputs()
  dfa.run(sys.argv[1])
