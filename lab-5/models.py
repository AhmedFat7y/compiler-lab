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

  def get_terms(self):
    terms_list = []
    for term in self.body:
      symbols_list = []
      for symbol in term:
        symbols_list.append(symbol[0])
      terms_list.append(symbols_list)
    return terms_list

  def __repr__(self):
    out = self.head + '->['
    for term in self.body:
      for symbol in term:
        out += symbol[0]
      if self.body[-1] != term:
        out += ', '
    return out + ']'
