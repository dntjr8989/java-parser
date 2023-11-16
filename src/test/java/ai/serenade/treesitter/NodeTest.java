package ai.serenade.treesitter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.Test;

public class NodeTest extends TestBase {

  @Test
  void testGetChildren() throws UnsupportedEncodingException {
    try (Parser parser = new Parser()) {
      parser.setLanguage(Languages.c());

      try (Tree tree = parser.parseString("#include <stdio.h>\n" +
              "\n" +
              "typedef struct _loc {\n" +
              "\tint y, x;\n" +
              "}loc;\n" +
              "\n" +
              "int N, M, lab[50][50], ans = -1;\n" +
              "loc virus[10], activate[2500];\n" +
              "int v = 0, tlab[50][50], empty = 0, filled;\n" +
              "\n" +
              "void spread(int idx, int size) {\n" +
              "\tif (filled == empty) {\n" +
              "\t\tif ((ans == -1) || (ans > tlab[activate[size - 1].y][activate[size - 1].x]))\n" +
              "\t\t\tans = tlab[activate[size - 1].y][activate[size - 1].x];\n" +
              "\t\treturn;\n" +
              "\t}\n" +
              "\tint i, tsize = size;\n" +
              "\tfor (i = idx; i < tsize; ++i) {\n" +
              "\t\tif (activate[i].y && (tlab[activate[i].y - 1][activate[i].x] < -1)) {\n" +
              "\t\t\tif (tlab[activate[i].y - 1][activate[i].x] == -3) ++filled;\n" +
              "\t\t\ttlab[activate[i].y - 1][activate[i].x] = tlab[activate[i].y][activate[i].x] + 1;\n" +
              "\t\t\tactivate[size].y = activate[i].y - 1;\n" +
              "\t\t\tactivate[size++].x = activate[i].x;\n" +
              "\t\t}\n" +
              "\t\tif ((activate[i].y < N - 1) && (tlab[activate[i].y + 1][activate[i].x] < -1)) {\n" +
              "\t\t\tif (tlab[activate[i].y + 1][activate[i].x] == -3) ++filled;\n" +
              "\t\t\ttlab[activate[i].y + 1][activate[i].x] = tlab[activate[i].y][activate[i].x] + 1;\n" +
              "\t\t\tactivate[size].y = activate[i].y + 1;\n" +
              "\t\t\tactivate[size++].x = activate[i].x;\n" +
              "\t\t}\n" +
              "\t\tif (activate[i].x && (tlab[activate[i].y][activate[i].x - 1] < -1)) {\n" +
              "\t\t\tif (tlab[activate[i].y][activate[i].x - 1] == -3) ++filled;\n" +
              "\t\t\ttlab[activate[i].y][activate[i].x - 1] = tlab[activate[i].y][activate[i].x] + 1;\n" +
              "\t\t\tactivate[size].y = activate[i].y;\n" +
              "\t\t\tactivate[size++].x = activate[i].x - 1;\n" +
              "\t\t}\n" +
              "\t\tif ((activate[i].x < N - 1) && (tlab[activate[i].y][activate[i].x + 1] < -1)) {\n" +
              "\t\t\tif (tlab[activate[i].y][activate[i].x + 1] == -3) ++filled;\n" +
              "\t\t\ttlab[activate[i].y][activate[i].x + 1] = tlab[activate[i].y][activate[i].x] + 1;\n" +
              "\t\t\tactivate[size].y = activate[i].y;\n" +
              "\t\t\tactivate[size++].x = activate[i].x + 1;\n" +
              "\t\t}\n" +
              "\t}\n" +
              "\tif (size > tsize) spread(tsize, size);\n" +
              "}\n" +
              "\n" +
              "void pickM(int count, int idx) {\n" +
              "\tint i, j;\n" +
              "\tif (count == M) {\n" +
              "\t\tfor (i = 0; i < N; ++i)\n" +
              "\t\t\tfor (j = 0; j < N; ++j)\n" +
              "\t\t\t\tif (!lab[i][j]) tlab[i][j] = -3;\n" +
              "\t\t\t\telse tlab[i][j] = lab[i][j];\n" +
              "\t\tfor (i = 0; i < M; ++i)\n" +
              "\t\t\ttlab[activate[i].y][activate[i].x] = 0;\n" +
              "\t\tfilled = 0;\n" +
              "\t\tspread(0, M);\n" +
              "\t\treturn;\n" +
              "\t}\n" +
              "\tfor (i = idx; i < v; ++i) {\n" +
              "\t\tactivate[count].y = virus[i].y;\n" +
              "\t\tactivate[count].x = virus[i].x;\n" +
              "\t\tpickM(count + 1, i + 1);\n" +
              "\t}\n" +
              "}\n" +
              "\n" +
              "int main() {\n" +
              "\tint i, j;\n" +
              "\tscanf(\"%d %d\", &N, &M);\n" +
              "\tfor(i = 0; i < N; ++i)\n" +
              "\t\tfor (j = 0; j < N; ++j) {\n" +
              "\t\t\tscanf(\"%d\", &lab[i][j]);\n" +
              "\t\t\tlab[i][j] *= -1;\n" +
              "\t\t\tif (!lab[i][j]) ++empty;\n" +
              "\t\t\telse if (lab[i][j] == -2) {\n" +
              "\t\t\t\tvirus[v].y = i;\n" +
              "\t\t\t\tvirus[v++].x = j;\n" +
              "\t\t\t}\n" +
              "\t\t}\n" +
              "\tpickM(0, 0);\n" +
              "\tprintf(\"%d\", ans);\n" +
              "\treturn 0;\n" +
              "}")) {
        Node root = tree.getRootNode();
        System.out.println("tree.getRootNode().getNodeString() = " + tree.getRootNode().getNodeString());
        /*
        assertEquals(1, root.getChildCount());
        assertEquals("module", root.getType());
        assertEquals(0, root.getStartByte());
        assertEquals(44, root.getEndByte());

        Node function = root.getChild(0);
        assertEquals("function_definition", function.getType());
        assertEquals(5, function.getChildCount());*/
      }
    }
  }
/*
  @Test
  void testGetChildren() throws UnsupportedEncodingException {
    try (Parser parser = new Parser()) {
      parser.setLanguage(Languages.python());
      try (Tree tree = parser.parseString("def foo(bar baz)\n  print(bar,)\n  print(baz)")) {
        Node root = tree.getRootNode();
        assert(rosot.hasError());
      }
    }
  }*/
}
