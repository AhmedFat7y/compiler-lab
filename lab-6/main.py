import first
import follow
import table_builder

from file_parser import parse


def convert_rules_list_to_dict(rules):
  rules_dict = {}
  for rule in rules:
    rules_dict[rule.head] = rule
  return rules_dict


if __name__ == "__main__":
  files = ['Sample4.in']
  for file in files:
    variables, terminals, rules = parse(file)
    rules_dict = convert_rules_list_to_dict(rules)
    first_sets = first.get(variables, rules_dict)
    follow_sets = follow.get(variables, rules, first_sets)
    # change terminals to include $ instead of !
    terminals[-1] = '$'
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
    print('              ---------------')
    for i in range(1, 5):
      output = parse('input{0}.in'.format(i), rules=False, terminals=terminals)
      print('-- Inputs: ')
      print('--', output)
      print('              ---------------')
    print ('              =====================')
