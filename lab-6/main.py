import first

from parser import parse
from models import Rule
# import os


if __name__ == '__main__':
  input_file = 'input%r.in'
  output_file = 'input%r.out'
  for i in range(1, 5):
    lines, rules = parse(input_file % i)
