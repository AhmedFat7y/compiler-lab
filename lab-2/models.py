'''To hold our models.'''

START_STATE = 0
NORMAL_STATE = 1
ACCEPT_STATE = 2
REJECT_STATE = 3


class State:
  '''Represent states in DFA.'''

  def __init__(self, name, state_type=NORMAL_STATE):
    '''Initialize the state object.'''
    self.name = name
    self.state_type = state_type
    self.lexical_category_name = 'N/A'
    # self.transitions = transitions

  def __str__(self):
    '''Return string representation of a state.'''
    state_types = ['START_STATE', 'NORMAL_STATE', 'ACCEPT_STATE', 'REJECT_STATE']
    return 'State: %s # %s' % (self.state, state_types[self.state_type])


class Transition:
  '''Represent transitions in DFA.'''

  def __init__(self, start_state, symbol, end_state):
    '''Initialize a transition.'''
    self.start_state = start_state
    self.symbol = symbol
    self.end_state = end_state

  def __str__(self):
    '''Return string representation of a transition.'''
    return 'Transition: (%s) => (%s) => (%s)' % (self.symbol, str(self.start_state), str(self.end_state))


class DFA:
  '''Represent a fallback DFA.'''

  def __init__(self):
    '''Initialize the DFA.'''
    self.alphabet = []
    self.states = []
    self.lexical_categories = []

  def set_alphabet(self, alphabet):
    '''Set alphabet for this DFA eg. [a, b].'''
    self.alphabet = alphabet

  def set_states(self, states):
    '''Set states for this DFA.'''
    self.states = states

  def __str__(self):
    '''Return string representation of DFA.'''
    return 'DFA with alphabet %s' % (str(self.alphabet))

  def run(input_str):
    """

    """
    pass
