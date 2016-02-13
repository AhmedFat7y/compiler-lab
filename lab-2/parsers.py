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
    for label in labels:
      for definition in definitions:
        if definition[1] == label[1]:
          raw_lexical_categories.append((definition[0], label[0], label[1]))
          break
    return raw_lexical_categories

  def extract_states(self):
    raw_states = self.data[0][0].split(',')
    start_state = self.data[3][0].split(',')
    accept_states = self.data[4][0].split(',')
    normal_states = set(raw_states) - (set(accept_states) | set(start_state))
    self.states.append(State(start_state[0], state_type=START_STATE))
    for normal_state in normal_states:
      self.states.append(State(normal_state))
    for accept_state in accept_states:
      self.states.append(State(accept_state, state_type=ACCEPT_STATE))
    return self.states

  def extract_alphabet(self):
    raw_alphabet = self.data[1][0].split(',')
    self.alphabet = raw_alphabet

  def extract_transitions(self):
    raw_transitions = self.data[2]
    self.transitions = [tuple(x.split(',')) for x in raw_transitions]
    return self.transitions

  def extract_lexical_categories(self):
    raw_labels = self.data[5]
    raw_definitions = self.data[6]
    labels = [tuple(x.split(',')) for x in raw_labels]
    definitions = [tuple(x.split(',')) for x in raw_definitions]
    self.lexical_categories = self.associate_definitions_with_states(labels, definitions)
    return self.lexical_categories

  def extract_data(self):
    self.extract_alphabet()
    self.extract_states()
    self.extract_transitions()
    self.extract_lexical_categories()

  def update_states_transitions(self):
    for transition in self.transitions:
      state1 = self.get_state(transition[0])
      state2 = self.get_state(transition[2])
      state1.add_transition(transition[1], state2)

  def update_states_lexical_categories(self):
    for lexical_category in self.lexical_categories:
      state = self.get_state(lexical_category[1])
      state.set_lexical_category(lexical_category[0], lexical_category[2])

  def construct_dfa(self):
    self.dfa = DFA()
    self.dfa.set_alphabet(self.alphabet)
    self.dfa.set_start_state(self.states[0])
    self.update_states_transitions()
    self.update_states_lexical_categories()
    return self.dfa

  def parse(self):
    self.read_input()
    self.prepare_raw_data()
    self.extract_data()
    return self.construct_dfa()
