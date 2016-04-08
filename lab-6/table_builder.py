from models import ParsingTable, Term, Symbol

EPSILON_TERM = Term([Symbol('!')])


def get_rule_term(variable, terminal, rule, first_sets, follow_sets):
  for term in rule.terms:
    if terminal in first_sets[term]:
      return term
  return None


def build_cell(variable, terminal, rule, first_sets, follow_sets):
  cell_content = None
  first_set = first_sets[variable]
  follow_set = follow_sets[variable]
  if terminal in first_set:
    term = get_rule_term(variable, terminal, rule, first_sets, follow_sets)
    cell_content = term
  elif terminal in follow_set:
    if '!' in first_set:
      cell_content = EPSILON_TERM
  return cell_content


def build(variables, terminals, first_sets, follow_sets, rules_dict):
  table = ParsingTable(variables, terminals)
  for variable in variables:
    rule = rules_dict[variable]
    for terminal in terminals:
      cell_content = build_cell(variable, terminal, rule, first_sets, follow_sets)
      table.add(variable, terminal, cell_content)
  return table
