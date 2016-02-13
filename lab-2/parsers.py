'''parsers to convert data from fallbackdfa.in file to our models.'''
from models import *


class Parser:
  '''Parser Class to parse fallbackdfa.in.'''

  def __init__(self, dfa_file, test_inputs_file):
    '''Initialize parser with file_name.'''
    self.dfa_file = dfa_file
    self.test_inputs_file = test_inputs_file
    # self.lines = []
    # self.data = []
    # self.states = []
    # self.transitions = []
    # self.lexical_categories = []

  def read_input(self, file_name):
    '''Read the input file.'''
    lines = []
    with open(file_name, 'r') as input_file:
      lines = [line.strip() for line in input_file.readlines()]
    return lines

  def prepare_raw_data(self, lines):
    '''Prepare raw lines into different sections for further parsing.
      When you find a #, add the following lines to the same data group.
    '''
    raw_data = []
    for line in lines:
      if line[0] == '#':
        raw_data.append([])
      else:
        raw_data[-1].append(line)
    return raw_data

  def get_state(self, states, state_name):
    for state in states:
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

  def extract_states(self, raw_states, raw_sart_state, raw_accept_states):
    states = []
    all_states = raw_states.split(',')
    start_state = raw_sart_state.split(',')
    accept_states = raw_accept_states.split(',')
    normal_states = set(all_states) - (set(accept_states) | set(start_state))
    states.append(State(start_state[0], state_type=START_STATE))
    for normal_state in normal_states:
      states.append(State(normal_state))
    for accept_state in accept_states:
      states.append(State(accept_state, state_type=ACCEPT_STATE))
    return states

  def extract_alphabet(self, raw_alphabet):
    return raw_alphabet.split(',')

  def extract_transitions(self, raw_transitions):
    transitions = [tuple(x.split(',')) for x in raw_transitions]
    return transitions

  def extract_lexical_categories(self, raw_labels, raw_definitions):
    lexical_categories = []
    labels = [tuple(x.split(',')) for x in raw_labels]
    definitions = [tuple(x.split(',')) for x in raw_definitions]
    lexical_categories = self.associate_definitions_with_states(labels, definitions)
    return lexical_categories

  def extract_data(self, raw_data):
    states = self.extract_states(raw_data[0][0], raw_data[3][0], raw_data[4][0])
    alphabet = self.extract_alphabet(raw_data[1][0])
    transitions = self.extract_transitions(raw_data[2])
    lexical_categories = self.extract_lexical_categories(raw_data[5], raw_data[6])
    return states, alphabet, transitions, lexical_categories

  def update_states_transitions(self, transitions, states):
    for transition in transitions:
      state1 = self.get_state(states, transition[0])
      state2 = self.get_state(states, transition[2])
      state1.add_transition(transition[1], state2)

  def update_states_lexical_categories(self, lexical_categories, states):
    for lexical_category in lexical_categories:
      state = self.get_state(states, lexical_category[1])
      state.set_lexical_category(lexical_category[0], lexical_category[2])

  def construct_dfa(self, states, alphabet, transitions, lexical_categories):
    self.dfa = DFA()
    self.dfa.set_alphabet(alphabet)
    self.dfa.set_start_state(states[0])
    self.update_states_transitions(transitions, states)
    self.update_states_lexical_categories(lexical_categories, states)
    return self.dfa

  def parse_dfa(self):
    lines = self.read_input(self.dfa_file)
    raw_data = self.prepare_raw_data(lines)
    states, alphabet, transitions, lexical_categories = self.extract_data(raw_data)
    return self.construct_dfa(states, alphabet, transitions, lexical_categories)

  def parse_test_inputs(self):
    lines = self.read_input(self.test_inputs_file)
    test_inputs = []
    for i in range(0, len(lines), 2):
      test_inputs.append((lines[i], lines[i + 1]))
    return test_inputs
