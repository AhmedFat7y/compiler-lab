'''parsers to convert data from fallbackdfa.in file to our models.'''
from models import *


class Parser:
  '''Parser Class to parse fallbackdfa.in.'''

  def __init__(self, file_name):
    '''Initialize parser with file_name.'''
    self.file_name = file_name
    self.lines = []
    self.data = []
    self.states = []
    self.transitions = []
    self.lexical_categories = []

  def read_input(self):
    '''Read the input file.'''
    with open(self.file_name, 'r') as input_file:
      self.lines = [line.strip() for line in input_file.readlines()]

  def prepare_raw_data(self):
    '''Prepare raw lines into different sections for further parsing.
      When you find a #, add the following lines to the same data group.
    '''
    for line in self.lines:
      if line[0] == '#':
        self.data.append([])
      else:
        self.data[-1].append(line)

  def get_state(self, state_name):
    for state in self.states:
      if state.name == state_name:
        return state

  def associate_definitions_with_states(self, labels, definitions):
    raw_lexical_categories = []
    for definition in definitions:
      raw_lexical_categories.append([])
      for label in labels:
        if definition[1] == label[1]:
          lexical_categories[-1].append((definition[0], label[0], label[1]))
    return raw_lexical_categories

  def extract_states(self):
    raw_states = self.data[0][0].split(',')
    start_state = self.data[3][0].split(',')
    accept_states = self.data[4][0].split(',')
    self.states = []
    for raw_state in raw_states:
      state = State(raw_state)
      if raw_state in accept_states:
        state.state_type = ACCEPT_STATE
      elif raw_state in start_state:
        state.state_type = START_STATE
      self.states.append(state)
    self.states.append(State('reject', REJECT_STATE))
    return self.states

  def extract_alphabet(self):
    raw_alphabet = self.data[1].split(',')
    self.alphabet = raw_alphabet

  def extract_transitions(self):
    raw_transitions = self.data[2]
    self.transitions = map(lambda x: tuple(x.split(',')), raw_transitions)
    return self.transitions

  def extract_lexical_categories(self):
    raw_labels = self.data[5]
    raw_definitions = self.data[6]
    labels = map(lambda x: tuple(x.split(',')), raw_labels)
    definitions = map(lambda x: tuple(x.split(',')), raw_definitions)
    self.lexical_categories = self.associate_definitions_with_states(labels, definitions)
    return self.lexical_categories

  def extract_data(self):
    self.extract_alphabet()
    self.extract_states()
    self.extract_transitions()
    self.extract_lexical_categories()

  def parse(self):
    self.read_input()
    self.prepare_raw_data()
    self.extract_data()
    return self.dfa
