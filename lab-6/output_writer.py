def write_table(parsing_table):
  with open('table.out', 'w') as f:
    f.write(str(parsing_table))


def format_data(raw_parse_tree):
  results = []
  for top_symbol, input_symbol, rule_used in raw_parse_tree:
    result = ''
    if top_symbol == input_symbol:
      result = '{0}--->{0}'.format(top_symbol)
    else:
      result = '{0}[{1}]--->{2}'.format(top_symbol, input_symbol, rule_used)
    result += '\n'
    results.append(result)
  return results


def write_parse_tree(output_file_name, isSucess, raw_parse_tree):
  parse_tree = ['Parse Error.']
  if isSucess:
    parse_tree = format_data(raw_parse_tree)
  with open(output_file_name, 'w') as f:
    f.writelines(parse_tree)
