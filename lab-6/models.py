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

  def __repr__(self):
    result = self.head
    result += ' -> '
    # result+= '['
    result += ' | '.join(map(str, self.terms))
    # result += ']'
    return result


class Term:
  def __init__(self, symbols_list=None):
    if symbols_list:
      self.symbols = symbols_list
    else:
      self.symbols = []

  def add(self, symbol):
    self.symbols.append(symbol)

  def has_epsilon(self):
    for symbol in self.symbols:
      if symbol.is_epsilon():
        return True
    return False

  def __repr__(self):
    result = ''.join(map(str, self.symbols))
    return result


class Symbol:
  def __init__(self, symbol_char, isVariable=False):
    self.symbol_char = symbol_char
    self.isVariable = isVariable

  def is_epsilon(self):
    return self.symbol_char == '!'

  def __repr__(self):
    return self.symbol_char
