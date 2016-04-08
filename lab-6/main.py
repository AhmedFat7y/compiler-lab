import first
import follow
import table_builder
import file_parser
import input_parser
import output_writer


def convert_rules_list_to_dict(rules):
  rules_dict = {}
  for rule in rules:
    rules_dict[rule.head] = rule
  return rules_dict


if __name__ == "__main__":
  rules_files = ['Sample4.in']
  input_string_file_template = 'input{0}.in'
  output_parse_tree_file_template = 'my-ouput{0}.out'
  parse_trees = []
  for file in rules_files:
    variables, terminals, rules = file_parser.parse(file)
    rules_dict = convert_rules_list_to_dict(rules)
    first_sets = first.get(variables, rules_dict)
    follow_sets = follow.get(variables, rules, first_sets)
    # change terminals to include $ instead of !
    terminals[-1] = '$'
    parsing_table = table_builder.build(variables, terminals, first_sets, follow_sets, rules_dict)
    print('Rules: ')
    print('\n'.join(map(str, rules)))
    print('              ---------------')
    print('First Sets: ')
    print(first_sets)
    print('              ---------------')
    print('Follow Sets: ')
    print(follow_sets)
    print('              ---------------')
    print('Parsing Table: ')
    print(parsing_table)
    print('              ---------------')
    for i in range(1, 5):
      input_file_name = input_string_file_template.format(i)
      output_file_name = output_parse_tree_file_template.format(i)
      input_to_parse = file_parser.parse(input_file_name, rules=False, terminals=terminals)
      sucess, parse_tree = input_parser.parse(input_to_parse[0], variables[0], parsing_table)
      parse_trees.append((output_file_name, sucess, parse_tree))
      print('-- Input: ')
      print('--', input_to_parse[0])
      print('---- Parse Tree: =>', sucess)
      print('\n'.join(map(str, parse_tree)))
    print('              =====================')
    output_writer.write_table(parsing_table)
    for output_file_name, sucess, parse_tree in parse_trees:
      output_writer.write_parse_tree(output_file_name, sucess, parse_tree)
