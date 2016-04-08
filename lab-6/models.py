class Rule:
  '''list of terms'''
  def __init__(self, head, terms_list=None):
    self.head = head
    if terms_list:
      self.terms = terms_list
    else:
      self.terms = []

  def add(self, term=None, body=None):
    if term:
      self.addTerm(term)
    elif body:
      self.addBody(body)
    else:
      print("WHERE THE HELL ARE YOU HERE?!")

  def addTerm(self, term):
    self.terms.append(term)

  def addBody(self, body):
    self.terms.extend(body)

  def has_epsilon(self):
    for term in self.terms:
      if term.has_epsilon():
        return True
    return False

  def __hash__(self):
    return hash(self.head) + hash(tuple(self.terms))

  def __eq__(self, other):
    if isinstance(other, Rule):
      return self.head == other.head and self.terms == other.terms
    else:
      return other == self

  def __repr__(self):
    result = self.head
    result += ' -> '
    # result+= '['
    result += '|'.join(map(str, self.terms))
    # result += ']'
    return result


class Term:
  def __init__(self, symbols=None):
    if symbols:
      self.symbols = symbols
    else:
      self.symbols = []

  def add(self, symbol):
    self.symbols.append(symbol)

  def has_epsilon(self):
    for symbol in self.symbols:
      if symbol.is_epsilon():
        return True
    return False

  def __len__(self):
    return len(self.symbols)

  def __hash__(self):
    return hash(tuple(self.symbols))

  def __eq__(self, other):
    if isinstance(other, Term):
      return self.symbols == other.symbols
    else:
      return other == self

  def __repr__(self):
    result = ''.join(map(str, self.symbols))
    return result


class Symbol:
  def __init__(self, symbol_char, isVariable=False):
    self.symbol_char = symbol_char
    self.isVariable = isVariable

  def is_epsilon(self):
    return self.symbol_char == '!'

  def __hash__(self):
    return hash(self.symbol_char) + hash(self.isVariable)

  def __eq__(self, other):
    if isinstance(other, Symbol):
      return self.symbol_char == other.symbol_char
    elif isinstance(other, str):
      return self.symbol_char == other
    else:
      return other == self

  def __repr__(self):
    return self.symbol_char


class ParsingTable:
  def __init__(self, variables, terminals):
    self.variables = variables
    self.terminals = terminals
    self.table_content = {}

  def add(self, variable, terminal, term):
    self.table_content[(variable, terminal)] = term

  def get(self, variable, terminal):
    if (variable, terminal) not in self.table_content:
      return None
    cell_content = self.table_content[(variable, terminal)]
    return cell_content if cell_content else Term([Symbol('null')])

  def __repr__(self):
    max_length = 3 + max(map(lambda x: len(str(x)) if x else 0, self.table_content.values()))
    # decrement max_length by 1 because of comma added
    result = (' ' * max_length) + ''.join(map(lambda x: x.ljust(max_length), self.terminals))
    result += '\n'
    for variable in self.variables:
      result += variable.ljust(max_length)
      for terminal in self.terminals:
        result += str(self.get(variable, terminal)).ljust(max_length)
      result += '\n'
    return result
