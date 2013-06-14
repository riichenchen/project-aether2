#include <cstdio>
#include <cstring>

using namespace std;

const char* d[8] = {"down", "downleft", "downright", "left", "right", "up", "upleft", "upright"};
char s[8][128], buf[128];
int n;

int main() {
	freopen("data.in", "r", stdin);
	freopen("data.out", "w", stdout);
	int num_mobs = 0;
	while (scanf("%s%d", buf, &n) != EOF) {
		printf("%s\n", buf);
		for (int i = 0; i < n; i++) {
			scanf("%s", s[i]);
			for (int j = 0; j < 8; j++)
				printf("%s%s\n", s[i], d[j]);
		}
		printf("/%s\n", buf);
		num_mobs++;
	}
	//printf("%d\n", num_mobs);
	return 0;
}