#include <list>
#include <array>
#include <deque>
#include <queue>
#include <stack>
#include <tuple>
#include <string>
#include <vector>
#include <cstdint>
#include <cstddef>
#include <numeric>
#include <iostream>
#include <algorithm>
#include <unordered_map>
#include <unordered_set>
using namespace std;

#pragma GCC optimize(3, "Ofast", "inline", "no-stack-protector", "unroll-loops")
static const auto faster_streams = [] {
    srand(time(nullptr));
    // use time to init the random seed
    std::ios::sync_with_stdio(false);
    std::istream::sync_with_stdio(false);
    std::ostream::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie(nullptr);
    // 关闭c++风格输入输出 , 与C风格输入输出的同步,提高性能.
    return 0;
}();
int main() {

    int T;
    cin >> T;
    while (T--) {
        int n;
        cin >> n;
        vector<int> next(n);
        vector<char> isV(n);
        for (int i = 0; i < n; ++i) {
            int xi;
            string s;
            cin >> xi >> s;
            next[i] = xi - 1;
            isV[i] = (s == "villager");
        }
        vector<char> state(n, 0);
        vector<char> can(n);
        vector<int> terminal(n);
        vector<int> stk(n);
        int top = 0;
        for (int i = 0; i < n; ++i) {
            if (state[i] != 0) continue;
            int v = i;
            while (true) {
                stk[top++] = v;
                state[v] = 1;
                if (isV[v]) {
                    int nx = next[v];
                    if (state[nx] == 0) {
                        v = nx;
                        continue;
                    } else if (state[nx] == 1) {
                        while (top > 0) {
                            int w = stk[--top];
                            can[w] = 1;
                            terminal[w] = -1;
                            state[w] = 2;
                        }
                        break;
                    } else {
                        int u = terminal[nx];
                        if (u == -1) {
                            while (top > 0) {
                                int w = stk[--top];
                                can[w] = 1;
                                terminal[w] = -1;
                                state[w] = 2;
                            }
                            break;
                        } else {
                            int X = next[u];
                            bool tailHasX = !can[nx];
                            bool prefixHasX = false;
                            while (top > 0) {
                                int w = stk[--top];
                                if (w == X) prefixHasX = true;
                                bool forced = tailHasX || prefixHasX;
                                can[w] = !forced;
                                terminal[w] = u;
                                state[w] = 2;
                            }
                            break;
                        }
                    }
                } else {
                    int u = v;
                    int X = next[u];
                    bool seenX = false;
                    while (top > 0) {
                        int w = stk[--top];
                        if (w == X) seenX = true;
                        can[w] = !seenX;
                        terminal[w] = u;
                        state[w] = 2;
                    }
                    break;
                }
            }
        }
        int cnt = 0;
        for (int i = 0; i < n; ++i) if (!can[i]) cnt++;
        cout << cnt << '\n';
    }
    return 0;
}
