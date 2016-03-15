from parser import parse
from models import Rule
import os


def get_common_prefixes(terms):
  common_prefixes = {}
  i = 0
  for current_term in terms:
    for other_term in terms:
      if current_term == other_term:
        continue
      if current_term[i] == other_term[i]:
        if current_term[i] not in common_prefixes:
          common_prefixes[current_term[i]] = 0
        common_prefixes[current_term[i]] += 1
  return common_prefixes


def _left_factor(rule):
  '''rule: Rule Object'''
  terms = rule.get_terms()
  common_prefixes = {}
  common_prefixes = get_common_prefixes(terms)
  print(terms)
  print(common_prefixes)


def left_factor(rules):
  '''rules: List of Rule objects'''
  for rule in rules:
    _left_factor(rule)


if __name__ == '__main__':
  parent = 'LeftFactoring'
  files = ['sample.in', 'sample2.in', 'sample3.in']
  for file_name in files:
    lines, raw_rules = parse(os.path.join(parent, file_name))
    rules = []
    for head, body in raw_rules.items():
      rules.append(Rule(head, body))
    for rule in rules:
      _left_factor(rule)
