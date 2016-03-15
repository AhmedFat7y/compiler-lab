class Rule:
  '''
  provides interface to query the raw representation of rules returned by the parser
  '''
  def __init__(self, head, body):
    '''
    head: a symbol to represent the head
    body: list of the body symbols in this format => [[('terminal', False), ('non-terminal', 'True')], [('terminal', False)]]
    '''
    self.head = head
    self.body = body
    self.terms = []
    self.terms = self.get_terms()

  def set_terms(self, terms):
    self.terms = terms

  def get_terms(self):
    if self.terms:
      return self.terms
    for term in self.body:
      symbols_list = []
      for symbol in term:
        symbols_list.append(symbol[0])
      self.terms.append(symbols_list)
    return self.terms

  def __repr__(self):
    out = self.head + '->['
    for term in self.terms:
      for symbol in term:
        out += symbol
      if self.terms[-1] != term:
        out += ', '
    return out + ']'
