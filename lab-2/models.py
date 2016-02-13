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
    self.states_stack = []
    self.start_index = 0
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

  def push(self, symbol, state):
    self.states_stack.append((symbol, state))

  def pop(self):
    return self.states_stack.pop()

  def peek(self):
    return reversed(self.states_stack[self.start_index:])

  def update_index(self):
    self.start_index = len(self.states_stack)

  def get_output(self):
    for symbol, state in self.peek():
      if state.state_type == ACCEPT_STATE:
        break
      self.pop()
    else:
      return ''
    output = ''.join([symbol for symbol, state in self.states_stack[self.start_index:]])
    return output

  def __repr__(self):
    '''Return string representation of DFA.'''
    return 'DFA with alphabet %r' % (str(self.alphabet))

  def subrun(self, input_str):
    current_state = self.start_state
    for c in input_str:
      current_state = current_state.next(c)
      if not current_state:
        return self.get_output()
      else:
        self.push(c, current_state)

  def run(self, input_str):
    """
    """
    output = []
    while len(self.states_stack) != len(input_str):
      current_output = self.subrun(input_str[len(self.states_stack):])
      if current_output:
        self.update_index()
        output.append(current_output)
      elif input_str[self.start_index] not in self.alphabet:
        break
    else:
      output.append(self.get_output())
    print(output)
