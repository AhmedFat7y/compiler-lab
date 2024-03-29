import re

from models import Term, Rule, Symbol


def read_input_file(input_file_name):
  lines = []
  with open(input_file_name) as f:
    lines = [line.strip() for line in f.readlines()]
  return lines


def divide_data(lines):
  return lines[0], lines[1], lines[2:]


def parse_variables(raw_variables):
  variables = raw_variables.split(',')
  return variables


def parse_terminals(raw_terminals):
  terminals = raw_terminals.split(',')
  terminals.append('!')
  return terminals


def build_regex(items):
  items = reversed(sorted(items, key=lambda x: len(x)))
  pattern = '|'.join([re.escape(item) for item in items])
  return pattern


def parse_rule_body(raw_body, variables, terminals):
  variables_regex = build_regex(variables)
  terminals_regex = build_regex(terminals)
  pattern = re.compile("(?P<variable>{0})|(?P<terminal>{1})".format(variables_regex, terminals_regex))
  body = []
  for raw_term in raw_body.split('|'):
    term = pattern.findall(raw_term)
    body.append(term)
  return body


def parse_rules(raw_rules, variables, terminals):
  '''rules are spanning 2 lines. 1st line is for head, 2nd line is for body'''
  rules = {}
  for i in range(0, len(raw_rules), 2):
    raw_head = raw_rules[i]
    raw_body = raw_rules[i + 1]
    body = parse_rule_body(raw_body, variables, terminals)
    rules[raw_head] = body
  return rules


def build_term(term):
  term_obj = Term()
  for variable, terminal in term:
    if variable:
      symbol_obj = Symbol(variable, True)
    elif terminal:
      symbol_obj = Symbol(terminal, False)
    else:
      print("WHY THE HELL ARE YOU HERE?! building term")
    term_obj.add(symbol_obj)
  return term_obj


def build_body(rule_body):
  terms = []
  for term in rule_body:
    terms.append(build_term(term))
  return terms


def build_rules(rules):
  rules_objs = []
  for head, body in rules.items():
    rule = Rule(head)
    body = build_body(body)
    rule.add(body=body)
    rules_objs.append(rule)
  return rules_objs


def parse_input_rules(lines):
  raw_vriables, raw_terminals, raw_rules = divide_data(lines)
  terminals = parse_terminals(raw_terminals)
  variables = parse_variables(raw_vriables)
  rules = parse_rules(raw_rules, variables, terminals)
  rules = build_rules(rules)
  return variables, terminals, rules


def parse_input(lines, terminals):
  output = []
  for str_to_parse in lines:
    terminal_regex = build_regex(terminals)
    pattern = re.compile('({0})'.format(terminal_regex))
    output.append(pattern.findall(str_to_parse))
  return output


def parse(input_file_name, rules=True, terminals=None):
  lines = read_input_file(input_file_name)
  if rules:
    return parse_input_rules(lines)
  elif terminals:
    return parse_input(lines, terminals)
  else:
    print('YOU SHALL NOT PASS.')
