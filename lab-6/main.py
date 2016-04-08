import first
import follow
import table_builder

from parser import parse
# import os


def convert_rules_list_to_dict(rules):
  rules_dict = {}
  for rule in rules:
    rules_dict[rule.head] = rule
  return rules_dict


if __name__ == "__main__":
  files = ['Sample4.in', 'Sample5.in']
  for file in files:
    variables, terminals, rules = parse(file)
    rules_dict = convert_rules_list_to_dict(rules)
    first_sets = first.get(variables, rules_dict)
    follow_sets = follow.get(variables, rules, first_sets)
    parsing_table = table_builder.build(variables, terminals, first_sets, follow_sets, rules_dict)
    print('Rules: ')
    print('\n'.join(map(str, rules)))
    print('              ---------------')
    # print('First Sets: ')
    # print(first_sets)
    # print('              ---------------')
    # print('Follow Sets: ')
    # print(follow_sets)
    # print('              ---------------')
    print('Parsing Table: ')
    print(parsing_table)
    print ('              =====================')
