from parser import parse
from models import Rule
import os


def copy_rule_body(rule_body):
  return [list(term) for term in rule_body]


def get_common_prefixes(terms):
  common_prefixes = {}
  i = 0
  for current_term in terms:
    if current_term[i] in common_prefixes:
      continue
    common_prefixes[current_term[i]] = 1
    for other_term in terms:
      if current_term[i] == other_term[i] and current_term != other_term:
        common_prefixes[current_term[i]] += 1
  return common_prefixes


def get_max_prefix(common_prefixes):
  max_count = 0
  max_prefix = 'N/A'
  for prefix, count in common_prefixes.items():
    if count > max_count:
      max_prefix = prefix
      max_count = count
  return max_prefix, max_count


def _left_factor(head, terms, prefix, count):
  new_head = head + "^"
  new_terms = []
  mod_terms = [[prefix, new_head]]
  for term in terms:
    if term[0] == prefix:
      if len(term) != 1:
        new_terms.append(term[1:])
    else:
      mod_terms.append(term)
  return mod_terms, new_head, new_terms


def left_factor(rules):
  '''rules: List of Rule objects'''
  common_prefix_count = 1
  while common_prefix_count:
    common_prefix_count = 0
    for rule in list(rules):
      terms = rule.get_terms()
      # print('terms--', terms)
      common_prefixes = {}
      common_prefixes = get_common_prefixes(terms)
      max_prefix, max_count = get_max_prefix(common_prefixes)
      print(common_prefixes)
      print(max_count, max_prefix)
      common_prefix_count += max_count - 1
      if max_count < 2:  # we don't need to do anymore work if the max count of prefix occurence is 1 or 0
        continue
      mod_terms, new_head, new_terms = _left_factor(rule.head, terms, max_prefix, max_count)
      rule.set_terms(mod_terms)
      new_rule = Rule(new_head, [])
      new_rule.set_terms(new_terms)
      rules.append(new_rule)
    print('reloop')

if __name__ == '__main__':
  parent = 'LeftFactoring'
  files = ['sample.in', 'sample2.in', 'sample3.in']
  for file_name in files:
    lines, raw_rules = parse(os.path.join(parent, file_name))
    rules = []
    for head, body in raw_rules.items():
      rules.append(Rule(head, body))
    print(rules)
    left_factor(rules)
    print(rules)
    print('=========')
