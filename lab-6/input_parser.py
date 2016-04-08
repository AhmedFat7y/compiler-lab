from models import Symbol


def is_terminal(term):
  for symbol in term.symbols:
    if symbol.isVariable:
      return False
  return True


def is_epsilon(term):
  for symbol in term.symbols:
    if symbol.symbol_char == '!':
      return True
  return False


def match_end_of_input(pda_stack, used_rules, parsing_table):
  # remove $ from beginning
  for top_symbol in reversed(pda_stack[1:]):
    print('- Popped variable {0}'.format(top_symbol))
    cell_content = parsing_table.get(str(top_symbol), '$')
    if not cell_content:
      return False
    used_rules.append((top_symbol, '$', cell_content))
    if not is_epsilon(cell_content):
      return False
    print('- FOUND $')
  return True


def match_terminal(input_terminal, pda_stack, used_rules, parsing_table):
  while True:
    if not pda_stack:
      print('- PDA IS EMPTY')
      return False
    top_symbol = pda_stack.pop()  # note
    print('- Popped variable {0}'.format(top_symbol))
    if top_symbol.symbol_char == '$':
      print('- FOUND $')
      return False
    elif not top_symbol.isVariable:
      print('- FOUND TERMINAL: {0}'.format(top_symbol))
      used_rules.append((top_symbol, top_symbol, top_symbol))
      return True
    cell_content = parsing_table.get(str(top_symbol), input_terminal)
    if not cell_content:
      return False
    used_rules.append((top_symbol, input_terminal, cell_content))  # note
    if is_epsilon(cell_content):
      print('- Found Epsilon, go to next variable.')
    else:
      new_symbols = list(reversed(cell_content.symbols))
      pda_stack.extend(new_symbols)
      print('- Added {0} to stack'.format(new_symbols))


def parse(input_terminals, initial_variable, parsing_table):
  pda_stack = [Symbol('$'), Symbol(initial_variable, True)]
  used_rules = []  # tuple of (variable, termnal, rule)
  for input_terminal in input_terminals:
    if not match_terminal(input_terminal, pda_stack, used_rules, parsing_table):
      return False, used_rules

  return match_end_of_input(pda_stack, used_rules, parsing_table), used_rules
