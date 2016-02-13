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
    self.transitions = {}

  def add_transition(self, symbol, next_state):
    self.transitions[symbol] = next_state

  def set_lexical_category(self, lexical_category_name, expression):
    self.lexical_category_name = lexical_category_name
    self.expression = expression

  def next(self, symbol):
    if symbol in self.transitions:
      return self.transitions[symbol]
    else:
      return None

  def __unicode__(self):
    return 'State: %r' % self.name

  def __repr__(self):
    '''Return string representation of a state.'''
    state_types = ['START_STATE', 'NORMAL_STATE', 'ACCEPT_STATE', 'REJECT_STATE']
    return '%r: %r - Lexical Category: %r' % (state_types[self.state_type], self.name, self.lexical_category_name)


class DFA:
  '''Represent a fallback DFA.'''

  def __init__(self):
    '''Initialize the DFA.'''
    self.alphabet = []
    self.start_state = None
    # self.current_state = None

  def set_alphabet(self, alphabet):
    '''Set alphabet for this DFA eg. [a, b].'''
    self.alphabet = alphabet

  def set_start_state(self, start_state):
    '''Set states for this DFA.'''
    self.start_state = start_state
    # self.current_state = start_state

  def reset(self):
    # self.set_start_state(self.start_state)
    pass

  def push(self, states_stack, symbol, state):
    states_stack.append((symbol, state))

  def pop(self, states_stack):
    return states_stack.pop()

  def peek(self, states_stack):
    return reversed(states_stack)

  def update_index(self, states_stack):
    return len(states_stack)

  def get_output(self, states_stack):
    accept_state = None
    for symbol, state in self.peek(states_stack):
      if state.state_type == ACCEPT_STATE:
        accept_state = state
        break
      self.pop(states_stack)
    return accept_state, ''.join([symbol for symbol, state in states_stack])

  def __repr__(self):
    '''Return string representation of DFA.'''
    return 'DFA with alphabet %r' % (str(self.alphabet))

  def subrun(self, input_str, states_stack):
    current_state = self.start_state
    for c in input_str:
      current_state = current_state.next(c)
      if not current_state:
        return self.get_output(states_stack)
      else:
        self.push(states_stack, c, current_state)
    else:
      return self.get_output(states_stack)

  def run(self, input_str):
    """
    """
    output = []
    states_stack = []
    # print(input_str)
    while len(input_str) != len(states_stack):
      if input_str[0] not in self.alphabet:
        state = State('')
        state.lexical_category_name = ''
        output.append((state, 'Error!'))
        break
      current_output = self.subrun(input_str, states_stack)
      # print ('output: ', current_output)
      # print ('output: ', states_stack)
      if not current_output.count(None):
        output.append(current_output)
        input_str = input_str[len(states_stack):]
        states_stack = []
    else:
      # print('else while')
      # output.append(self.get_output(states_stack))
      # print(states_stack)
      pass
    return output
