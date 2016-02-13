'''main.py main method.'''
from parsers import Parser

if __name__ == '__main__':

  parser = Parser('fallbackdfa.in', 'Lab2.in')
  dfa = parser.parse_dfa()
  test_outputs = []
  test_inputs = parser.parse_test_inputs()
  for test_input_name, test_input_data in test_inputs:
    print('--', test_input_name)
    test_output = dfa.run(test_input_data)
    test_outputs.append((test_input_name.replace('in', 'out'), test_output))
  with open('Lab2-2.out', 'w') as f:
    for test_output_name, test_output_data in test_outputs:
      # print('---', test_output_name)
      # print(test_output_data)
      formatted_test_output = ['%s,%s' % (state.lexical_category_name, lexeme) for state, lexeme in test_output_data]
      if formatted_test_output:
        formatted_test_output = '%s\n%s' % (test_output_name, ' '.join(formatted_test_output))
      else:
        formatted_test_output = test_output_name + "\nError: Input lexemes don't match the language!"
      f.write(formatted_test_output)
      f.write('\n')
