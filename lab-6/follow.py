from first import contains_epsilon, clean_data


def get_follow_for_variable_in_term(rule_head, follow_sets, first_sets, term, variable_index):
  follow_set = []
  # symbol = term.symbols[variable_index]
  for i in range(variable_index + 1, len(term.symbols)):
    current_symbol = term.symbols[i]
    if current_symbol.isVariable:
      temp_first_set = first_sets[current_symbol.symbol_char]
      follow_set.extend(temp_first_set)
      if contains_epsilon(temp_first_set):
        follow_set.remove('!')
      else:
        break
    else:
      follow_set.append(current_symbol.symbol_char)
      break
  else:  # no break encountered, meaning it kept looping till the end
    if rule_head in follow_sets:
      follow_set.extend(follow_sets[rule_head])
  return follow_set


def get_follow_for_term(rule_head, follow_sets, first_sets, term):
  for i in range(len(term.symbols)):
    symbol = term.symbols[i]
    key = symbol.symbol_char
    if symbol.isVariable:
      if key not in follow_sets:
        follow_sets[key] = []
      follow_sets[key].extend(get_follow_for_variable_in_term(rule_head, follow_sets, first_sets, term, i))
  return follow_sets


def get(variables, rules, first_sets):
  follow_sets = {variables[0]: ['$']}
  for i in range(len(variables)):
    for rule in rules:
      for term in rule.terms:
        get_follow_for_term(rule.head, follow_sets, first_sets, term)
  follow_sets = clean_data(follow_sets)
  return follow_sets
