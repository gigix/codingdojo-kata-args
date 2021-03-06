package org.codingdojo.kata.args;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Lists.newArrayList;

public class Parser {
    private final List<Argument> arguments;

    public Parser(Schema schema, String inputText) {
        arguments = newArrayList();
        Matcher matcher = Pattern.compile("(-[a-z] [^-]*)").matcher(inputText);
        while(matcher.find()) {
            arguments.add(new Argument(matcher.group(1), schema));
        }

        for (Flag flag : schema.getFlags()) {
            Collection<Argument> matchingArgs = filter(arguments, argument -> argument.getName().equals(flag.getName()));
            if(matchingArgs.isEmpty()) {
                arguments.add(new NullArgument(flag));
            }
        }
    }

    public int getArgumentSize() {
        return arguments.size();
    }

    public Object getArgumentValue(String argumentName) {
        return newArrayList(filter(arguments, argument -> argument.getName().equals(argumentName))).get(0).getValue();
    }
}
