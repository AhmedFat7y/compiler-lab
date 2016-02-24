import re


def format_output(rules, heads):
  output_lines = []
  heads_prime = [head + "'" for head in heads]
  for head in (heads + heads_prime):
    tail = []
    if head not in rules:
      continue
    tail = rules[head]
    # rfactor'->[*rfactor', !]
    result = head + '->['
    for term in tail:
      for terminal_variable, _ in term:
        result += terminal_variable
      if tail[-1] != term:
        result += ', '
    result += ']\n'
    output_lines.append(result)
  return output_lines


def write_output_file(input_file_name, rules, heads):
  output_file_name = re.sub(r'\.in$', '.out.out', input_file_name)
  with open(output_file_name, 'w') as f:
    output_lines = format_output(rules, heads)
    f.writelines(output_lines)


def read_input_file(input_file_name):
  lines = []
  with open(input_file_name) as f:
    lines = f.readlines()
  return [line.rstrip() for line in lines]


# {'head': 'tail'} => tail = 'terminal|non-terminal' combination
def get_rules_from_lines(lines):
  rules = {}
  for i in range(0, len(lines), 2):
    rules[lines[i]] = lines[i + 1]
  return rules


def get_variable_at_start(term, heads):
  minIndex = len(term) + 1
  minHead = None
  for head in heads:
    index = term.find(head)
    if index < 0:
      continue
    elif index < minIndex:
      minIndex = index
      minHead = head
  return minIndex, minHead


# return list of ('terminal', False) or ('non-terminal', True)
def parse_terminals_or_variables(term, head, head_index_in_term):
  if head_index_in_term == 0:
    return [(head, True)]
  else:
    return [(term[:head_index_in_term], False), (head, True)]


def parse_term(term, heads):
  term_terminals_variables = []  # list of terminals/non-terminals
  while term:
    head_index_in_term, head_in_term = get_variable_at_start(term, heads)
    if not head_in_term:
      term_terminals_variables.append((term, False))
      term = ''
    else:
      term_terminals_variables.extend(parse_terminals_or_variables(term, head_in_term, head_index_in_term))
      term = term[len(head_in_term) + head_index_in_term:]
  return term_terminals_variables


# return tail in this format [('terminal', True), ('non-terminal', False)]
def parse_tail(raw_tail, heads):
  tail = []  # list of terms
  for term in raw_tail.split('|'):
    tail.append(parse_term(term, heads))
  return tail


def parse_rules(rules):
  heads = [key for key in rules]
  for head, tail in rules.items():
    tail = parse_tail(tail, heads)
    rules[head] = tail
  return rules


def parse(input_file_name):
  """
  returns dict of rules in this format
  rules = {'head': [[('terminal', False), ('non-terminal', 'True')], [('terminal', False)]]}
  """
  lines = read_input_file(input_file_name)
  rules = get_rules_from_lines(lines)
  rules = parse_rules(rules)
  return lines, rules
