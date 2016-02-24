from parser import parse, write_output_file
""" Format of rule for A -> Axx|Ass|epsilon
The head is the key in the rules dictionary and 'Axx|Ass|epsilon' is the value
'Axx|Dss|epsilon' is represnted as follows
[[('A', True), ('x', False), ('x', False)], [('A', True), ('s', False), ('s', False)], ['!']]
'Axx' is called Term
"""


def represent_rule(head, tail):
  out = head + '->['
  for term in tail:
    for symbol in term:
      out += symbol[0]
    if tail[-1] != term:
      out += ', '
  return out + ']'


def print_rules(rules):
  print('\n'.join([represent_rule(head, tail) for head, tail in rules.items()]))


def remove_immediat_recursion(rule_head, tail):
  prime_rule_head = rule_head + "'"  # create A'
  prime_tail = []
  for term in tail[::]:  # assume rule_head = A
    most_left_symbol = term[0]
    if rule_head == most_left_symbol[0]:  # found A -> Axxxx
      tail.pop(0)  # remove Axxxx
      term.remove(most_left_symbol)  # remove A
      prime_tail.append(term)  # move xxxx to A'
    term.append((prime_rule_head, True))  # add A' whether it's the recursion one or not
  prime_tail.append([('!', False)])  # add list of episol, represinting it's last term
  return prime_rule_head, prime_tail


def contains_immediate_recursion(rule_head, tail):
  for term in tail:
    most_left_symbol = term[0]
    if rule_head == most_left_symbol[0]:
      return True
  return False


def replace_occurences(rule_to_process, rule_to_replace):  # E -> Ax, A -> Edf|xe
  # print('--', represent_rule(rule_to_process[0], rule_to_process[1]))
  # print('--', represent_rule(rule_to_replace[0], rule_to_replace[1]))
  for main_term in rule_to_process[1][::]:
    for symbol in main_term[::]:
      if symbol[0] == rule_to_replace[0]:
        replacement_index = main_term.index(symbol)
        main_term.remove(symbol)
        rule_to_process[1].remove(main_term)
        for replace_term in rule_to_replace[1][::]:
          temp_term = main_term[::]
          for replace_symbol in replace_term:
            temp_term.insert(replacement_index, replace_symbol)
          rule_to_process[1].append(temp_term)


def remove_indirect_recursion(heads=[], rules={}):
  for i in range(len(heads)):
    rule_to_process = (heads[i], rules[heads[i]])
    for j in range(i):
      rule_to_replace = (heads[j], rules[heads[j]])
      replace_occurences(rule_to_process, rule_to_replace)
    if contains_immediate_recursion(rule_to_process[0], rule_to_process[1]):
      prime_rule = remove_immediat_recursion(rule_to_process[0], rule_to_process[1])
      rules[prime_rule[0]] = prime_rule[1]


# def remove_recursion(rules):
#   prime_rules = {}
#   for rule_head in rules:
#     if contains_immediate_recursion(rule_head, rules[rule_head]):
#       prime_rule = remove_immediat_recursion(rule_head, rules[rule_head])
#       prime_rules[prime_rule[0]] = prime_rule[1]
#   rules.update(prime_rules)

if __name__ == '__main__':
  file_name = 'Sample3.in'
  lines, rules = parse(file_name)
  heads = []
  for i in range(0, len(lines), 2):
    heads.append(lines[i])
  # print('--', heads)
  remove_indirect_recursion(heads, rules)
  # print_rules(rules)
  write_output_file(file_name, rules, heads)
