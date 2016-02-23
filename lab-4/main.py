# import os


def read_input_file():
  lines = []
  with open('Smaple.in') as f:
    lines = f.readlines()
  return lines

def start():
  read_input_file()

if __name__ == '__main__':
  start()
