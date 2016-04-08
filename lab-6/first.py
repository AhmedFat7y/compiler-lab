# from models import Rule, Term, Symbol


def get_first_of_symbol(symbol, first_sets, rules):
  pass


def clean_data(first_sets):
  for variable, first_set in first_sets.items():
    first_sets[variable] = set(first_set)
  return first_sets


def contains_epsilon(chars):
  for ch in chars:
    if ch == '!':
      return True
  return False


def get_first_of_term(term, first_sets, rules):
  first_set = []
  has_episoln = True
  for symbol in term.symbols:
    if symbol.isVariable:
      temp_first_set = []
      if symbol.symbol_char in first_sets:
        temp_first_set = first_sets[symbol.symbol_char]
      first_set.extend(temp_first_set)
      if contains_epsilon(temp_first_set):
        first_set.remove('!')
      else:
        has_episoln = False
        break
    else:  # if terminal
      first_set.append(symbol.symbol_char)
      has_episoln = False
      break
  if has_episoln:
    first_set.append('!')
  print (first_sets)
  return first_set


def get_first_of_rule(head, first_sets, rules):
  rule = rules[head]
  first_set = []
  for term in rule.terms:
    term_first_set = get_first_of_term(term, first_sets, rules)
    if term not in first_sets:
      first_sets[term] = []
    first_sets[term].extend(term_first_set)
    first_set.extend(term_first_set)
  return first_set


def get(variables, rules):
  first_sets = {}
  for i in range(len(variables)):
    for variable in variables:
      if variable not in first_sets:
        first_sets[variable] = []
      first_sets[variable].extend(get_first_of_rule(variable, first_sets, rules))
  first_sets = clean_data(first_sets)
  return first_sets
